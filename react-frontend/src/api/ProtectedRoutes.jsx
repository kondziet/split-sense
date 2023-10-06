import { Navigate, Outlet } from "react-router-dom";
import useAuthenticationContext from "../hooks/useAuthenticationContext";
import { useEffect, useState } from "react";

const ProtectedRoutes = () => {
  const { userAuthenticated } = useAuthenticationContext();
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    setLoading(false);
  }, [userAuthenticated]);

  if (loading) {
    return null;
  }

  if (!userAuthenticated) {
    return <Navigate to="/authentication" />;
  }

  return <Outlet />;
};

export default ProtectedRoutes;
