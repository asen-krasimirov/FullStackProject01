import { resetUser } from '../api/userApi.js';

const AdminPage = () => {
    const userId = 1;

    const handleReset = () => {
        resetUser(userId).then(() => alert("User reset complete."));
    };

    return (
        <div className="center-wrapper">
            <div className="page-container">
                <div>
                    <h1>Admin</h1>
                    <button onClick={handleReset} className="reset-button">Reset User</button>
                </div>
            </div>
        </div>
    );
};

export default AdminPage;
