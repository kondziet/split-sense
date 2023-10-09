import usePrivateClientRequest from "../hooks/usePrivateClientRequest";
import useRefreshToken from "../hooks/useRefreshToken";

const Dashboard = () => {
  const refresh = useRefreshToken();
  const sendPrivateRequest = usePrivateClientRequest();

  return (
    <div>
      <h1>Hello zelo</h1>
      <button onClick={() => refresh()}>click me</button>
      <button
        onClick={async () => {
          const response = await sendPrivateRequest.get("/api/hello");
          console.log(response);
        }}
      >
        protected route
      </button>
    </div>
  );
};

export default Dashboard;
