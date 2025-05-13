import './App.css'

import { BrowserRouter as Router, Route, Routes, Link } from 'react-router-dom';
import DashboardPage from './pages/DashboardPage';
import BuySellPage from './pages/BuySellPage';
import HistoryPage from './pages/HistoryPage';
import AdminPage from './pages/AdminPage';
import LivePricesPage from './pages/LivePricesPage';

function App() {
    return (
        <Router>
            <div>
                <nav>
                    <ul>
                        <li><Link to="/">Dashboard</Link></li>
                        <li><Link to="/trade">Buy/Sell</Link></li>
                        <li><Link to="/history">History</Link></li>
                        <li><Link to="/prices">Live Prices</Link></li>
                        <li><Link to="/admin">Admin</Link></li>
                    </ul>
                </nav>

                <Routes>
                    <Route path="/" element={<DashboardPage />} />
                    <Route path="/trade" element={<BuySellPage />} />
                    <Route path="/history" element={<HistoryPage />} />
                    <Route path="/prices" element={<LivePricesPage />} />
                    <Route path="/admin" element={<AdminPage />} />
                </Routes>
            </div>

        </Router>
    );
}

export default App;
