import { useState, useEffect } from "react";
import API from "../services/api";

function UserDashboard() {

  const [addresses, setAddresses] = useState([]);

  const [order, setOrder] = useState({
    cargoName: "",
    description: "",
    weight: "",
    count: "",
    loadingAddressId: "",
    unloadingAddressId: ""
  });

  // 🔹 Filter addresses
  const loadingAddresses = addresses.filter(
    (addr) => addr.type === "LOADING"
  );

  const unloadingAddresses = addresses.filter(
    (addr) => addr.type === "UNLOADING"
  );

  // 🔹 Fetch addresses
  useEffect(() => {
    fetchAddresses();
  }, []);

  const fetchAddresses = async () => {
    try {
      const res = await API.get("/api/user/addresses");
      setAddresses(res.data.data);
    } catch (err) {
      alert("Error fetching addresses");
    }
  };

  // 🔹 Place order
  const placeOrder = async () => {
    try {
      await API.post("/api/user/orders", order);
      alert("Order placed successfully");

      // Reset form
      setOrder({
        cargoName: "",
        description: "",
        weight: "",
        count: "",
        loadingAddressId: "",
        unloadingAddressId: ""
      });

    } catch (err) {
      alert(err.response?.data?.message || "Error placing order");
    }
  };

  return (
    <div>
      <h2>User Dashboard</h2>

      {/* Cargo Name */}
      <input
        placeholder="Cargo Name"
        value={order.cargoName}
        onChange={(e) =>
          setOrder({ ...order, cargoName: e.target.value })
        }
      />

      {/* Description */}
      <input
        placeholder="Description"
        value={order.description}
        onChange={(e) =>
          setOrder({ ...order, description: e.target.value })
        }
      />

      {/* Weight */}
      <input
        placeholder="Weight"
        type="number"
        value={order.weight}
        onChange={(e) =>
          setOrder({ ...order, weight: e.target.value })
        }
      />

      {/* Count */}
      <input
        placeholder="Count"
        type="number"
        value={order.count}
        onChange={(e) =>
          setOrder({ ...order, count: e.target.value })
        }
      />

      {/* Loading Address */}
      <select
        value={order.loadingAddressId}
        onChange={(e) =>
          setOrder({ ...order, loadingAddressId: e.target.value })
        }
      >
        <option value="">Select Loading Address</option>

        {loadingAddresses.map((addr) => (
          <option key={addr.id} value={addr.id}>
            {addr.city} - {addr.state}
          </option>
        ))}
      </select>

      {/* Unloading Address */}
      <select
        value={order.unloadingAddressId}
        onChange={(e) =>
          setOrder({ ...order, unloadingAddressId: e.target.value })
        }
      >
        <option value="">Select Unloading Address</option>

        {unloadingAddresses.map((addr) => (
          <option key={addr.id} value={addr.id}>
            {addr.city} - {addr.state}
          </option>
        ))}
      </select>

      <br /><br />

      {/* Place Order */}
      <button onClick={placeOrder}>Place Order</button>

      {/* Logout */}
      <button
        onClick={() => {
          localStorage.clear();
          window.location.href = "/";
        }}
      >
        Logout
      </button>
    </div>
  );
}

export default UserDashboard;