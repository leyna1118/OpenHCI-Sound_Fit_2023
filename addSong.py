import spotipy
from spotipy.oauth2 import SpotifyOAuth

# Set your credentials and scopes
SPOTIPY_CLIENT_ID = '4612d711f0a145faa74c91fb27e94343'
SPOTIPY_CLIENT_SECRET = '91ff07a9d5ef4dcaac08b948116872ec'
SPOTIPY_REDIRECT_URI = 'http://localhost:3000'
SCOPE = "playlist-modify-public"  # Necessary scope to read the current playing track

# Authenticate
sp = spotipy.Spotify(auth_manager=SpotifyOAuth(client_id=SPOTIPY_CLIENT_ID,
                                               client_secret=SPOTIPY_CLIENT_SECRET,
                                               redirect_uri=SPOTIPY_REDIRECT_URI,
                                               scope=SCOPE))

# Find the playlist ID of the playlist you want to add songs to
user_id = 'panyjtuwzpktcbws5yuonkdej'  # Your Spotify username or user ID
playlist_name = 'test'  # The name of the new playlist
description = 'test'  # A description for the new playlist

# Get a list of playlists for the user
playlists = sp.user_playlists(user_id)

# Search for the playlist with the given name
selected_playlist = None
for playlist in playlists['items']:
    if playlist['name'] == playlist_name:
        selected_playlist = playlist
        break

if selected_playlist:
    playlist_id = selected_playlist['id']
else:
    playlist = sp.user_playlist_create(user=user_id, name=playlist_name, public=True, description=description)
    print("Playlist not found. Created a new playlist.")
    playlist_id = playlist['id']

# Add the song to the playlist using its track URI
track_uri = 'spotify:track:1BxfuPKGuaTgP7aM0Bbdwr'  # Replace this with the Spotify track URI you want to add

if playlist_id:
    sp.playlist_add_items(playlist_id=playlist_id, items=[track_uri])
    print("Track added to the playlist.")
else:
    print("Unable to add track to the playlist.")


# Search for songs (using any keyword, you can use an empty string to get more random results)
results = sp.search(q='', type='track', limit=50)

# Filter songs based on tempo range (greater than 90 and less than 100)
filtered_songs = []
for track in results['tracks']['items']:
    tempo = track['tempo']
    if 90 < tempo < 100:
        filtered_songs.append(track)

# Check if any songs were found
if not filtered_songs:
    print("No songs found in the desired tempo range.")
    exit()

# Choose a random song from the filtered list
random_track = random.choice(filtered_songs)

# Print the name of the randomly chosen song (optional)
print("Randomly chosen song:", random_track['name'])

