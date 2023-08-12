import spotipy
from spotipy.oauth2 import SpotifyOAuth
import time
import requests
import json
import webbrowser
from datetime import datetime

# Blynk Settings
BLYNK_AUTH_TOKEN = "bAjlTM2rCNTsq-fuv13RiNNWLIs4T2nI"
PIN = "v1&v2&v3&v4&v5&v6&v7"
url = f"https://blynk.cloud/external/api/get?token={BLYNK_AUTH_TOKEN}&{PIN}"

# Spotify API Authentication
SPOTIPY_CLIENT_ID = '5c564042b8c845d1a9d5ad0e0f8249c1'
SPOTIPY_CLIENT_SECRET = '8b608810f2034c448285a852d88d7e83'
SPOTIPY_REDIRECT_URI = 'http://localhost/'
SCOPE = "user-read-playback-state app-remote-control user-modify-playback-state playlist-modify-public"
USER_ID = "a1rrpek1ade88uoq5vg8tiyu5"

sp = spotipy.Spotify(auth_manager=SpotifyOAuth(client_id=SPOTIPY_CLIENT_ID,
                                               client_secret=SPOTIPY_CLIENT_SECRET,
                                               redirect_uri=SPOTIPY_REDIRECT_URI,
                                               scope=SCOPE))

def track_played(track_id, data):
    """Mark a track as played in the data structure."""
    for playlist_name, tracks in data.items():
        for track in tracks:
            if track['id'] == track_id:
                track['played'] = 1

def user_input_done(duration, last_content, content):
    """Check if user input is done."""
    if last_content['v1'] != -1 and (content['v1'] != last_content['v1'] or content['v2'] != last_content['v2']):
        duration = time.time()
    elif duration != 0 and time.time() - duration > 5:
        print("2:", time.time() - duration > 5)
        duration = 0
        return duration, True
    return duration, False

def find_track(current_playback, content):
    track_id = ""
    
    seed_artists = [current_playback['item']['artists'][0]['id']]
    seed_tracks = [current_playback['item']['id']]
    
    target_acousticness = 0.1 * content['v1']
    target_danceability = 0.05 * (content['v1'] + content['v2'])
    target_energy = 0.1 * (content['v2'] - content['v1'])
    target_instrumentalness = 0.1 * content['v1']
    target_loudness = (1/3) * ((content['v4']/1023) + (content['v1'] + content['v2'])/10)-60
    target_tempo = content['v6']

    for playlist_name, tracks in data.items():
        for track in tracks:
            # Consider a small range around the target attributes
            if not track['played']\
            and abs(track['audio_features']['acousticness'] - target_acousticness) < 0.05\
            and abs(track['audio_features']['danceability'] - target_danceability) < 0.05\
            and abs(track['audio_features']['energy'] - target_energy) < 0.05\
            and abs(track['audio_features']['instrumentalness'] - target_instrumentalness) < 0.05\
            and abs(track['audio_features']['loudness'] - target_loudness) < 3\
            and abs(track['audio_features']['tempo'] - target_tempo) < 10:
                track['id']["played"] = True
                track_id =  track['id']

    recommendations = sp.recommendations(limit=10, seed_artists=seed_artists, seed_tracks=seed_tracks,
    target_acousticness=target_acousticness, target_danceability=target_danceability,
    target_energy=target_energy, target_instrumentalness=target_instrumentalness,
    target_loudness=target_loudness, target_tempo=target_tempo,
    min_valence=0.5, max_speechiness=0.5, min_popularity=80, market="TW")
    if recommendations and recommendations['tracks']:
        track_id = recommendations['tracks'][0]['id']
    return track_id

def id_to_uri(track_id: str) -> str:
    return f"spotify:track:{track_id}"

# Fetching all playlists
playlists = sp.current_user_playlists()['items']

# Storing playlist data
data = {}

# Looping through each playlist to retrieve track details
print("Here is your all tracks:")
print("------------------------------------------")
for playlist in playlists:
    playlist_name = playlist['name']
    data[playlist_name] = []
    tracks = sp.playlist_items(playlist['id'])['items']
    print(f"Playlist: {playlist_name}")
    for track_info in tracks:
        track = track_info['track']
        audio_features = sp.audio_features(track['id'])[0]
        track_data = {
            'id': track['id'],
            'playlist': playlist_name,
            'played': 0,
            'audio_features': audio_features if audio_features else None
        }
        data[playlist_name].append(track_data)
        artist_name = track['artists'][0]['name'] if track['artists'] else "Unknown Artist"
        print(f" - {track['name']} by {artist_name}")  # you can add audio_features info here if needed
    print("------------------------------------------")
print("==========================================")

# Create a playlist named with today's date and date and time
now = datetime.now()
playlist_name = now.strftime("%Y-%m-%d %H:%M:%S")
user_id = USER_ID
playlist = sp.user_playlist_create(user_id, playlist_name)
playlist_id = playlist['id']
playlist_url = f"https://open.spotify.com/playlist/{playlist_id}"

song_not_found = True
while song_not_found:
    # Get song name from user
    song_name = input("Enter the name of the song you want to play:\n")

    # Search for the song on Spotify
    results = sp.search(q=song_name, limit=1)  # Assuming you want the first result

    # Check if a result was found and add it to the playlist
    if results['tracks']['items']:
        track_id = results['tracks']['items'][0]['id']
        sp.playlist_add_items(playlist_id, [track_id])
        # Play the playlist
        webbrowser.open(playlist_url)
        time.sleep(5)
        devices = sp.devices()['devices']
        sp.start_playback(context_uri=playlist_url)
        song_not_found = False
    else:
        print("Song not found.")

# Main Loop
current_id = ""
last_content = {"v1":-1,"v2":-1,"v3":-1,"v4":-1,"v5":-1,"v6":-1,"v7":-1}
duration = 0
current_volume = 50
next_track_found = False
next_track_found_again = False

while True:
    # Fetch Blynk input
    response = requests.get(url)
    if response.status_code == 200:
        content = json.loads(response.text)
        print(content)
    if not content:
        sys.stderr.write(f"Failed to retrieve content. HTTP Status Code: {response.status_code}")

    # Fetch currently playing track details
    current_playback = sp.current_playback()
    if current_playback and current_playback['item']['id'] != current_id:
        track_played(current_playback['item']['id'], data)
        print(f"Currently playing: {current_playback['item']['name']}")
        current_volume = current_playback['device']['volume_percent']
        current_id = current_playback['item']['id']
        next_track_found = False
        next_track_found_again = False

    # Check if user input is done
    duration, user_input = user_input_done(duration, last_content, content)
    print(duration, user_input)

    # Decide the next action based on user input and current song status
    if current_playback:
        current_position = current_playback['progress_ms']
        if user_input or not next_track_found or (not next_track_found_again and current_playback['item']['duration_ms'] - current_position < 10000):
            print("Finding next track:")
            if next_track_found:
                sp.playlist_remove_all_occurrences_of_items(playlist_id, [next_id])
                print(f" - {next_track['name']} by {next_track['artists'][0]['name']} removed from to the current playlist.")
                next_track_found_again = True
            next_id = id_to_uri(find_track(current_playback, content))
            sp.playlist_add_items(playlist_id, [next_id])
            next_track = sp.track(next_id)
            print(f" - {next_track['name']} by {next_track['artists'][0]['name']} added to the current playlist.")
            next_track_found = True

    # Go to the next song if user input done
    if current_playback and user_input:
        # Decrease volume to 0
        for i in range(current_volume, 0, -1):
            sp.volume(i)
            time.sleep(0.1)
        print("User input. Go to next song.")
        sp.next_track()
        # Restore original volume
        for i in range(0, current_volume):
            sp.volume(i)
            time.sleep(0.1)
        sp.volume(current_volume)

    # Store the current content for comparison in the next loop
    last_content = content
