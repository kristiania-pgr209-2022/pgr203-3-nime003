import {useEffect, useState} from 'react'
import './App.css'

function refreshPage() {
    setTimeout(reloadPage, 100);

    function reloadPage(){
        window.location.reload();
    }
}


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
            <div><label>Product Brand: <input type={"number"} value={productBrand} onChange={event => setProductBrand(event.target.value)}/></label></div>
            <div><label>Product name: <input type={"text"} value={productName} onChange={event => setProductName(event.target.value)}/></label></div>
            <button onClick={refreshPage}>submit</button>
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
  return <ul>{products.map(p => <div>product brand {p.productBrand} : {p.productName}</div>)}</ul>
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