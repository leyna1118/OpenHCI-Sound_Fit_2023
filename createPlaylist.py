import spotipy
from spotipy.oauth2 import SpotifyOAuth

# Authenticate your application with Spotipy
sp = spotipy.Spotify(auth_manager=SpotifyOAuth(client_id='e702c35d10a54fcabc165d3a52593e2d',
                                               client_secret='bcb2eaedaff84d069d5c71c6e27ff683',
                                               redirect_uri='http://localhost/',
                                               scope='playlist-modify-public')) # Change the scope if you want a different type of playlist access

# Create a new playlist
user_id = 'panyjtuwzpktcbws5yuonkdej'  # Your Spotify username or user ID
playlist_name = 'test'  # The name of the new playlist
description = 'test'  # A description for the new playlist
playlist = sp.user_playlist_create(user=user_id, name=playlist_name, public=True, description=description)

# Add tracks to the playlist
track_uris = ['SPOTIFY_TRACK_URI_1', 'SPOTIFY_TRACK_URI_2', ...]  # Replace these with the Spotify track URIs you want to add
sp.playlist_add_items(playlist_id=playlist['id'], items=track_uris)

