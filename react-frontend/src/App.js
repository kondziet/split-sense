import { BrowserRouter, Route, Routes } from "react-router-dom";
import AuthenticationPage from "./pages/AuthenticationPage";
import { AuthenticationProvider } from "./context/AuthenticationContext";
import HomePage from "./pages/HomePage";
import AppLayout from "./components/AppLayout";
import Dashboard from "./components/Dashboard";
import ProtectedRoutes from "./api/ProtectedRoutes";

function App() {
  return (
    <div>
      <BrowserRouter>
        <AuthenticationProvider>
          <Routes>
            <Route path="/" element={<HomePage />} />
            <Route path="/authentication" element={<AuthenticationPage />} />
            <Route element={<ProtectedRoutes />}>
                <Route element={<AppLayout />}>
                  <Route path="/dashboard" element={<Dashboard />} />
                </Route>
            </Route>
          </Routes>
        </AuthenticationProvider>
      </BrowserRouter>
    </div>
  );
}

export default App;
