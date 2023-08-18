import threading
import spotipy
from spotipy.oauth2 import SpotifyOAuth
import time
import requests
import json
import webbrowser
import random
from datetime import datetime

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

def user_input_time(last_input_time, last_user_input, user_input):
    """Check if user input is done."""
    if last_user_input['v1'] != -1 and (user_input['v1'] != last_user_input['v1'] or user_input['v2'] != last_user_input['v2']):
        last_input_time = time.time()
    elif last_input_time != 0 and time.time() - last_input_time > 5:
        last_input_time = 0
        return last_input_time, True
    return last_input_time, False

tracks = [["4uOBL4DDWWVx4RhYKlPbPC", "4uOBL4DDWWVx4RhYKlPbPC", "4uOBL4DDWWVx4RhYKlPbPC", "4uOBL4DDWWVx4RhYKlPbPC", "4uOBL4DDWWVx4RhYKlPbPC", ],
          ["4uOBL4DDWWVx4RhYKlPbPC", "4uOBL4DDWWVx4RhYKlPbPC", "4uOBL4DDWWVx4RhYKlPbPC", "4uOBL4DDWWVx4RhYKlPbPC", "4uOBL4DDWWVx4RhYKlPbPC", ],
          ["4uOBL4DDWWVx4RhYKlPbPC", "4uOBL4DDWWVx4RhYKlPbPC", "4uOBL4DDWWVx4RhYKlPbPC", "4uOBL4DDWWVx4RhYKlPbPC", "4uOBL4DDWWVx4RhYKlPbPC", ],
          ["4uOBL4DDWWVx4RhYKlPbPC", "4uOBL4DDWWVx4RhYKlPbPC", "4uOBL4DDWWVx4RhYKlPbPC", "4uOBL4DDWWVx4RhYKlPbPC", "4uOBL4DDWWVx4RhYKlPbPC", ],
          ["4uOBL4DDWWVx4RhYKlPbPC", "4uOBL4DDWWVx4RhYKlPbPC", "4uOBL4DDWWVx4RhYKlPbPC", "4uOBL4DDWWVx4RhYKlPbPC", "4uOBL4DDWWVx4RhYKlPbPC", ]]

def find_track(user_input):
    if user_input['v1'] > 5:
        user_input['v1'] = user_input['v1'] - 1
    if user_input['v2'] > 5:
        user_input['v2'] = user_input['v2'] - 1

    i = user_input['v1'] // 2
    j = user_input['v2'] // 2
    print(i, j)

    return tracks[i][j]
    
last_user_input = {'v1':5, 'v2':5}
user_input = {'v1':5, 'v2':5}
last_input_time = 0

# For fetching the input
input_cnt = 0

# Create a playlist named with today's date and date and time
now = datetime.now()
playlist_name = now.strftime("%Y-%m-%d %H:%M:%S")
user_id = USER_ID
playlist = sp.user_playlist_create(user_id, playlist_name)
playlist_id = playlist['id']
playlist_url = f"https://open.spotify.com/playlist/{playlist_id}"
sp.playlist_add_items(playlist_id, ["4uOBL4DDWWVx4RhYKlPbPC"])
sp.start_playback(context_uri=playlist_url)
current_id = ""
time.sleep(1)

while True:
    # Fetch the input
    input_cnt = input_cnt + 1
    print(input_cnt, user_input['v1'], user_input['v2'])
    if input_cnt == 100: 
        user_input = {'v1':random.randint(0, 10), 'v2':random.randint(0, 10)}
    if input_cnt > 200: 
        input_cnt = 0

    # Fetch currently playing track details
    current_playback = sp.current_playback()
    if current_playback:
        if current_playback['item']['id'] != current_id:
            print(f"Current playing: {current_playback['item']['name']}")
            current_volume = current_playback['device']['volume_percent']
            current_id = current_playback['item']['id']
            try:
                recommendations = sp.recommendations(limit=1, seed_tracks=[current_playback['item']['id']], seed_artists=[current_playback['item']['artists'][0]['id']], market="TW")
                next_track = recommendations['tracks'][0]
                next_id = next_track['id']
                sp.playlist_add_items(playlist_id, [next_id])
                print(f" - {next_track['name']} by {next_track['artists'][0]['name']} added to the current playlist.")
            except Exception as e:
                print(f"Error getting recommendations: {e}")

        # Check if user input is done
        last_input_time, user_input_done = user_input_time(last_input_time, last_user_input, user_input)

        if user_input_done:
            sp.playlist_remove_all_occurrences_of_items(playlist_id, [next_id])
            print(f" - Last song {next_track['name']} by {next_track['artists'][0]['name']} removed from the current playlist.")
            print(f"Finding next track with user input Mental:{user_input['v1']} and Concentration:{user_input['v2']}...")
            
            next_id = find_track(user_input)
            next_track = sp.track(next_id)
            sp.playlist_add_items(playlist_id, [next_id])
            print(f" - {next_track['name']} by {next_track['artists'][0]['name']} added to the current playlist.")
            
            # Decrease volume to 0
            for i in range(current_volume, 0, -1):
                sp.volume(i)
                time.sleep(0.05)
            print("User input. Go to next song.")
            sp.next_track()
            # Restore original volume
            for i in range(0, current_volume):
                sp.volume(i)
                time.sleep(0.05)
            sp.volume(current_volume)

    # Store the current user_input for comparison in the next loop
    last_user_input = user_input