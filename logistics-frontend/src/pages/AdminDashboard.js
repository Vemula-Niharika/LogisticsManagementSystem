import { useEffect, useState } from "react";
import API from "../services/api";

function AdminDashboard() {

  const [orders, setOrders] = useState([]);
  const [trucks, setTrucks] = useState([]);
  const [drivers, setDrivers] = useState([]);
  const [addresses, setAddresses] = useState([]);

  // Forms
  const [address, setAddress] = useState({ city:"", state:"", pincode:"", type:"LOADING" });
  const [truck, setTruck] = useState({ number:"", capacity:"" });
  const [driver, setDriver] = useState({ name:"", email:"", phone:"" });

  useEffect(() => {
    fetchAll();
  }, []);

  const fetchAll = async () => {
    const o = await API.get("/api/admin/orders");
    const t = await API.get("/api/admin/trucks");
    const d = await API.get("/api/admin/drivers");
    const a = await API.get("/api/admin/addresses");

    setOrders(o.data.data);
    setTrucks(t.data.data);
    setDrivers(d.data.data);
    setAddresses(a.data.data);
  };

  // =========================
  // CREATE ADDRESS
  // =========================
  
   const createAddress = async () => {
   try {
    await API.post("/api/admin/addresses", address);
    alert("Address added");
    fetchAll();
  } catch (err) {
    alert(err.response?.data?.message || "Error");
  }
};
  // =========================
  // CREATE TRUCK
  // =========================
  const createTruck = async () => {
  try {
    await API.post("/api/admin/trucks", truck);
    alert("Truck added");
    fetchAll();
  } catch (err) {
    alert(err.response?.data?.message || "Error");
  }
};

  // =========================
  // CREATE DRIVER
  // =========================
  const createDriver = async () => {
  try {
    await API.post("/api/admin/drivers", driver);
    alert("Driver added");
    fetchAll();
  } catch (err) {
    alert(err.response?.data?.message || "Error");
  }
};

  // =========================
  // ASSIGN
  // =========================
  const assignTruck = async (orderId, truckId) => {
  try {
    await API.put(`/api/admin/orders/${orderId}/truck/${truckId}`);
    fetchAll();
  } catch (err) {
    alert(err.response?.data?.message || "Error assigning truck");
  }
};

 const assignDriver = async (orderId, driverId) => {
  try {
    await API.put(`/api/admin/orders/${orderId}/driver/${driverId}`);
    fetchAll();
  } catch (err) {
    alert(err.response?.data?.message || "Error assigning driver");
  }
};

  const completeOrder = async (orderId) => {
    await API.put(`/api/admin/orders/${orderId}/complete`);
    fetchAll();
  };

  return (
    <div>
      <h2>Admin Dashboard</h2>

      {/* ADDRESS */}
      <h3>Add Address</h3>
      <input placeholder="City" onChange={(e)=>setAddress({...address, city:e.target.value})}/>
      <input placeholder="State" onChange={(e)=>setAddress({...address, state:e.target.value})}/>
      <input placeholder="Pincode" onChange={(e)=>setAddress({...address, pincode:e.target.value})}/>
      <select onChange={(e)=>setAddress({...address, type:e.target.value})}>
        <option value="LOADING">LOADING</option>
        <option value="UNLOADING">UNLOADING</option>
      </select>
      <button onClick={createAddress}>Add Address</button>

      {/* TRUCK */}
      <h3>Add Truck</h3>
      <input placeholder="Number" onChange={(e)=>setTruck({...truck, number:e.target.value})}/>
      <input placeholder="Capacity" onChange={(e)=>setTruck({...truck, capacity:e.target.value})}/>
      <button onClick={createTruck}>Add Truck</button>

      {/* DRIVER */}
      <h3>Add Driver</h3>
      <input placeholder="Name" onChange={(e)=>setDriver({...driver, name:e.target.value})}/>
      <input placeholder="Email" onChange={(e)=>setDriver({...driver, email:e.target.value})}/>
      <input placeholder="Phone" onChange={(e)=>setDriver({...driver, phone:e.target.value})}/>
      <button onClick={createDriver}>Add Driver</button>

      {/* VIEW DATA */}
      <h3>Addresses</h3>
      {addresses.map(a => <p key={a.id}>{a.city} ({a.type})</p>)}

      <h3>Trucks</h3>
      {trucks.map(t => <p key={t.id}>{t.number} - {t.available ? "Available" : "Busy"}</p>)}

      <h3>Drivers</h3>
      {drivers.map(d => <p key={d.id}>{d.name} - {d.available ? "Available" : "Busy"}</p>)}

      {/* ORDERS */}
      <h3>Orders</h3>
      {orders.map(o => (
        <div key={o.id} style={{border:"1px solid", margin:"10px", padding:"10px"}}>
          <p>ID: {o.id}</p>
          <p>Status: {o.status}</p>

          {/* ONLY AVAILABLE TRUCKS */}
          <select onChange={(e)=>assignTruck(o.id, e.target.value)}>
            <option>Select Truck</option>
            {trucks.filter(t => t.available).map(t => (
              <option key={t.id} value={t.id}>{t.number}</option>
            ))}
          </select>

          {/* ONLY AVAILABLE DRIVERS */}
          <select onChange={(e)=>assignDriver(o.id, e.target.value)}>
            <option>Select Driver</option>
            {drivers.filter(d => d.available).map(d => (
              <option key={d.id} value={d.id}>{d.name}</option>
            ))}
          </select>

          <button onClick={()=>completeOrder(o.id)}>Complete</button>
            <button onClick={() => {
                    localStorage.clear();
                      window.location.href = "/";
              }}>
                Logout
                </button>
        </div>
      ))}
    </div>
  );
}

export default AdminDashboard;