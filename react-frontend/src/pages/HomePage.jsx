import { Link } from "react-router-dom";

const HomePage = () => {
  return (
    <div className="flex flex-col justify-center min-h-full bg-gray-100">
      <div className="mx-auto w-full sm:max-w-md md:max-w-lg">
        <h1>Home page</h1>
        <Link to={"/authentication"}>
          <h1>Authentication page</h1>
        </Link>
      </div>
    </div>
  );
};

export default HomePage;
