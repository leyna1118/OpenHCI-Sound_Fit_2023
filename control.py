from flask import Flask, jsonify, send_from_directory
import spotipy
from spotipy.oauth2 import SpotifyOAuth

app = Flask(__name__, static_url_path='', static_folder='static')

sp = spotipy.Spotify(auth_manager=SpotifyOAuth(client_id='e702c35d10a54fcabc165d3a52593e2d',
                                               client_secret='bcb2eaedaff84d069d5c71c6e27ff683',
                                               redirect_uri='http://localhost/',
                                               scope='playlist-read-private,playlist-read-collaborative'))

user_id = 'panyjtuwzpktcbws5yuonkdej'  # Your Spotify username or user ID

@app.route('/')
def index():
    return send_from_directory(app.static_folder, 'main.html')

@app.route('/get_playlists')
def get_playlists():
    playlists = sp.user_playlists(user_id)
    playlist_data = []
    for playlist in playlists['items']:
        playlist_name = playlist['name']
        cover_image = playlist['images'][0]['url'] if playlist['images'] else None
        uri = playlist['uri']
        playlist_data.append({'playlist_name': playlist_name, 'cover_image': cover_image, 'uri': uri})
    return jsonify(playlist_data)

if __name__ == "__main__":
    app.run(debug=True)
