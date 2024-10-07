from flask import Flask, render_template, request
import numpy as np
from scipy.stats import norm
import seaborn as sns
import matplotlib.pyplot as plt
import io
import base64

app = Flask(__name__)

# Black-Scholes Formula
def black_scholes(S, K, T, r, sigma, option_type="call"):
    d1 = (np.log(S/K) + (r + 0.5 * sigma**2) * T) / (sigma * np.sqrt(T))
    d2 = d1 - sigma * np.sqrt(T)

    if option_type == "call":
        return S * norm.cdf(d1) - K * np.exp(-r * T) * norm.cdf(d2)
    elif option_type == "put":
        return K * np.exp(-r * T) * norm.cdf(-d2) - S * norm.cdf(-d1)

@app.route("/", methods=["GET", "POST"])
def index():
    option_price = None
    img_data = None

    if request.method == "POST":
        # Get form inputs
        S = float(request.form.get("S"))
        K = float(request.form.get("K"))
        T = float(request.form.get("T"))
        r = float(request.form.get("r"))
        sigma = float(request.form.get("sigma"))
        option_type = request.form.get("option_type")

        # Calculate option price
        option_price = black_scholes(S, K, T, r, sigma, option_type)

        # Generate heatmap
        min_spot = float(request.form.get("min_spot"))
        max_spot = float(request.form.get("max_spot"))
        min_vol = float(request.form.get("min_vol"))
        max_vol = float(request.form.get("max_vol"))

        # Create data for heatmaps
        spot_prices = np.linspace(min_spot, max_spot, 10)
        volatilities = np.linspace(min_vol, max_vol, 10)

        call_prices = np.zeros((len(volatilities), len(spot_prices)))
        put_prices = np.zeros((len(volatilities), len(spot_prices)))

        for i, vol in enumerate(volatilities):
            for j, spot in enumerate(spot_prices):
                call_prices[i, j] = black_scholes(spot, K, T, r, vol, "call")
                put_prices[i, j] = black_scholes(spot, K, T, r, vol, "put")

        # Plot heatmap
        fig, ax = plt.subplots(1, 2, figsize=(12, 6))
        sns.heatmap(call_prices, annot=True, fmt=".2f", ax=ax[0], xticklabels=np.round(spot_prices, 2), yticklabels=np.round(volatilities, 2), cmap="viridis")
        ax[0].set_title("Call Option Prices")
        ax[0].set_xlabel("Spot Price")
        ax[0].set_ylabel("Volatility")

        sns.heatmap(put_prices, annot=True, fmt=".2f", ax=ax[1], xticklabels=np.round(spot_prices, 2), yticklabels=np.round(volatilities, 2), cmap="viridis")
        ax[1].set_title("Put Option Prices")
        ax[1].set_xlabel("Spot Price")
        ax[1].set_ylabel("Volatility")

        # Save plot to a string buffer
        buf = io.BytesIO()
        plt.savefig(buf, format='png')
        buf.seek(0)
        img_data = base64.b64encode(buf.getvalue()).decode('utf-8')

    return render_template("index.html", option_price=option_price, img_data=img_data)

if __name__ == "__main__":
    app.run(debug=True)
