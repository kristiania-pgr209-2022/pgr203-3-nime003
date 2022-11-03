import {useEffect, useState} from 'react'
import './App.css'


function AddProduct() {
    const [productBrand, setProductBrand] = useState("");
    const [productName, setProductName] = useState("");

    async function handleSubmit(e) {
        e.preventDefault();
        await fetch("/api/products", {
            method: "post",
            body: JSON.stringify({productBrand, productName}),
            headers: {
                "Content-Type": "application/json"
            }
        });

    }


    return <div>
        <form onSubmit={handleSubmit}>
            <div><label>Product Brand: <input type={"text"} value={productBrand} onChange={event => setProductBrand(event.target.value)}/></label></div>
            <div><label>Product name: <input type={"text"} value={productName} onChange={event => setProductName(event.target.value)}/></label></div>
            <button>submit</button>
        </form>
    </div>
}

function ListProducts() {
    const [loading, setLoading] = useState(true);
    const [products, setProducts] = useState([]);

    useEffect(() => {
        (async () => {
            const res = await fetch("api/products");
            setProducts(await res.json());
            setLoading(false);
        })();
    }, [])

  if (loading){
      return <div>Loading products ...</div>
  }
  return <ul>
      <div style={{fontWeight: "bold"}}>product brand : product name</div>
      {products.map(p => <div>{p.productBrand} : {p.productName}</div>)}
  </ul>
}

function App() {

  return (
    <div className="App">
        <AddProduct/>
        <ListProducts/>
    </div>
  )
}

export default App
