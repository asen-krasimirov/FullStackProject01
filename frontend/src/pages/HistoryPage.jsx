import { useEffect, useState } from 'react';
import { getTransactionHistory } from '../api/transactionApi';

const HistoryPage = () => {
    const [transactions, setTransactions] = useState([]);
    const userId = 1;

    useEffect(() => {
        getTransactionHistory(userId).then(res => setTransactions(res.data));
    }, []);

    return (
        <div className="history-container">
            <div className="history-content">
                <h1 className="history-title">📝 Transaction History</h1>
                <table className="transaction-table">
                    <thead>
                        <tr>
                            <th>Transaction Type</th>
                            <th>Currency</th>
                            <th>Amount</th>
                            <th>Price</th>
                            <th>Total Cost/Revenue</th>
                            <th>Date</th>
                        </tr>
                    </thead>
                    <tbody>
                        {transactions.map(transaction => (
                            <tr key={transaction.id} className={transaction.transactionType === 'buy' ? 'buy' : 'sell'}>
                                <td>{transaction.transactionType}</td>
                                <td>{transaction.currency}</td>
                                <td>{transaction.amount}</td>
                                <td>${transaction.price.toFixed(2)}</td>
                                <td>${transaction.totalCost.toFixed(2)}</td>
                                <td>{new Date(transaction.timestamp).toLocaleString()}</td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
};

export default HistoryPage;
