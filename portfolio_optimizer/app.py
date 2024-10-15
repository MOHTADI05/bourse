from flask import Flask, render_template, request, url_for
import yfinance as yf
import pandas as pd
import numpy as np
from datetime import datetime, timedelta
from scipy.optimize import minimize
import matplotlib.pyplot as plt
import os
from optimizer import optimize_portfolio

app = Flask(__name__)

# Create 'static' folder if it doesn't exist
if not os.path.exists('static'):
    os.makedirs('static')

@app.route('/')
def index():
    return render_template('index.html')

@app.route('/optimize', methods=['POST'])
def optimize():
    tickers = request.form.get('tickers').split(',')
    tickers = [ticker.strip() for ticker in tickers]
    start_date = datetime.today() - timedelta(days=5*365)
    end_date = datetime.today()
    
    # Optimize portfolio
    optimal_weights, optimal_return, optimal_volatility, sharpe_ratio = optimize_portfolio(tickers, start_date, end_date)
    
    # Plot the bar chart for weights
    fig, ax = plt.subplots(figsize=(10, 6))
    ax.bar(tickers, optimal_weights, color='skyblue')
    ax.set_xlabel('Assets')
    ax.set_ylabel('Optimal Weights')
    ax.set_title('Optimal Portfolio Weights')
    
    # Save the plot to the static folder
    chart_path = os.path.join('static', 'weights_chart.png')
    plt.savefig(chart_path)
    plt.close()

    # Prepare data for the frontend
    result = {
        'tickers': tickers,
        'optimal_weights': optimal_weights,
        'expected_return': optimal_return,
        'volatility': optimal_volatility,
        'sharpe_ratio': sharpe_ratio,
        'chart_url': url_for('static', filename='weights_chart.png')
    }
    
    # Pass zip explicitly to the template context
    return render_template('index.html', result=result, zip=zip)

if __name__ == '__main__':
    app.run(debug=True)
