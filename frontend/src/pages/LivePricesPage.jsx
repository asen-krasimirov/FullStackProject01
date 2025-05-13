import { useEffect, useState } from 'react';
import { getMarketPrices } from '../api/marketApi';

const LivePricesPage = () => {
    const [prices, setPrices] = useState({});
    
    useEffect(() => {
        const fetchPrices = () => {
            getMarketPrices().then(res => setPrices(res.data));
        };

        fetchPrices();
        const interval = setInterval(fetchPrices, 1000);

        return () => clearInterval(interval);
    }, []);

    return (
        <div className="prices-container">
            <div className="prices-content">
                <h1 className="prices-title">📈 Live Cryptocurrency Prices</h1>
                <div className="price-grid">
                    {Object.entries(prices).map(([currency, price]) => (
                        <div key={currency} className="price-card">
                            <h2>{currency}</h2>
                            <p>${price.toFixed(5)}</p>
                        </div>
                    ))}
                </div>
            </div>
        </div>
    );
};

export default LivePricesPage;
