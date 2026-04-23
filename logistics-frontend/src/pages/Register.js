import { useState } from "react";
import API from "../services/api";

function Register() {
  const [user, setUser] = useState({
    name: "",
    email: "",
    password: ""
  });

  const register = async () => {
    try {
      await API.post("/auth/register", user);
      alert("Registered successfully");

      // ✅ Redirect to login after register
      window.location.href = "/";

    } catch {
      alert("Registration failed");
    }
  };

  return (
    <div>
      <h2>Register</h2>

      <input
        placeholder="Name"
        onChange={(e) => setUser({ ...user, name: e.target.value })}
      />

      <input
        placeholder="Email"
        onChange={(e) => setUser({ ...user, email: e.target.value })}
      />

      <input
        type="password"
        placeholder="Password"
        onChange={(e) => setUser({ ...user, password: e.target.value })}
      />

      <br /><br />

      <button onClick={register}>Register</button>

      <br /><br />

      {/* ✅ BACK TO LOGIN */}
      <button onClick={() => window.location.href = "/"}>
        Back to Login
      </button>
    </div>
  );
}

export default Register;