import spotipy
from spotipy.oauth2 import SpotifyOAuth

# Set up your Spotify API credentials
client_id = 'e702c35d10a54fcabc165d3a52593e2d'
client_secret = 'bcb2eaedaff84d069d5c71c6e27ff683'
redirect_uri = 'http://localhost/'

# Initialize the Spotipy client
sp = spotipy.Spotify(auth_manager=SpotifyOAuth(client_id, client_secret, redirect_uri))

# Get the track ID of the specific song you're interested in
track_id = '0IAgufC1FlOg1nZMmRZxRr'  # Replace with the actual track ID

# Get the track information
track_info = sp.track(track_id)

# Extract the seed values from the track information
seed_artists = [artist['id'] for artist in track_info['artists']]
seed_tracks = [track_id]
seed_genres = []  # You might not get direct genre information from the track

# Get the audio features for the track using the track ID
audio_features = sp.audio_features(track_id)

# Print the extracted seeds
print("Seed Artists:", seed_artists)
print("Seed Tracks:", seed_tracks)
print("Seed Genres:", seed_genres)

# Print the audio features
print("Audio Features:", audio_features)