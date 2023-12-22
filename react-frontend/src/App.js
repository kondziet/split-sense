import { BrowserRouter, Route, Routes } from "react-router-dom";
import AuthenticationPage from "./pages/AuthenticationPage";
import { AuthenticationProvider } from "./context/AuthenticationContext";
import HomePage from "./pages/HomePage";
import AppLayout from "./components/AppLayout";
import Dashboard from "./pages/Dashboard";
import ProtectedRoutes from "./api/ProtectedRoutes";
import OAuth2Redirect from "./components/OAuth2Redirect";

function App() {
  return (
    <div className="h-screen">
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
            <Route path="/oauth2/redirect" element={<OAuth2Redirect />} />
            <Route path="*" element={<h1>Not Found</h1>} />
          </Routes>
        </AuthenticationProvider>
      </BrowserRouter>
    </div>
  );
}

export default App;
