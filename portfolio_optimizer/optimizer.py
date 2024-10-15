import yfinance as yf
import pandas as pd
import numpy as np
from scipy.optimize import minimize

def download_data(tickers, start_date, end_date):
    adj_close_df = pd.DataFrame()
    for ticker in tickers:
        data = yf.download(ticker, start=start_date, end=end_date)
        adj_close_df[ticker] = data['Adj Close']
    return adj_close_df

def calculate_log_returns(adj_close_df):
    return np.log(adj_close_df / adj_close_df.shift(1)).dropna()

def portfolio_performance(weights, log_returns, cov_matrix, risk_free_rate):
    expected_return = np.sum(log_returns.mean() * weights) * 252
    portfolio_variance = weights.T @ cov_matrix @ weights
    portfolio_std_dev = np.sqrt(portfolio_variance)
    sharpe_ratio = (expected_return - risk_free_rate) / portfolio_std_dev
    return expected_return, portfolio_std_dev, sharpe_ratio

def optimize_portfolio(tickers, start_date, end_date, risk_free_rate=0.02):
    adj_close_df = download_data(tickers, start_date, end_date)
    log_returns = calculate_log_returns(adj_close_df)
    cov_matrix = log_returns.cov() * 252

    def neg_sharpe(weights):
        _, _, sharpe = portfolio_performance(weights, log_returns, cov_matrix, risk_free_rate)
        return -sharpe

    num_assets = len(tickers)
    initial_weights = np.array([1/num_assets] * num_assets)
    constraints = {'type': 'eq', 'fun': lambda weights: np.sum(weights) - 1}
    bounds = [(0, 0.4) for _ in range(num_assets)]

    result = minimize(neg_sharpe, initial_weights, method='SLSQP', bounds=bounds, constraints=constraints)
    optimal_weights = result.x
    optimal_return, optimal_volatility, optimal_sharpe = portfolio_performance(optimal_weights, log_returns, cov_matrix, risk_free_rate)

    return optimal_weights, optimal_return, optimal_volatility, optimal_sharpe
