import requests
import time

BLYNK_AUTH_TOKEN = "bAjlTM2rCNTsq-fuv13RiNNWLIs4T2nI"
PIN = "v1&v2&v3&v4&v5&v6&v7"
url = f"https://blynk.cloud/external/api/get?token={BLYNK_AUTH_TOKEN}&{PIN}"

while True:
    response = requests.get(url)

    # Check if the request was successful
    if response.status_code == 200:
        content = response.text
        print(content)
    else:
        print(f"Failed to retrieve content. HTTP Status Code: {response.status_code}")
    
    time.sleep(0.5)  # Wait for 500 ms

# 放可以存成csv