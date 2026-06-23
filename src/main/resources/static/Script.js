let hasMore = true;

async function loadFirstPage() {

    const limit = document.getElementById("limit").value;
    const category = document.getElementById("category").value;

    // New search starts from the beginning
    document.getElementById("cursor").value = "";

    let url = `/products?limit=${limit}`;

    if (category !== "") {
        url += `&category=${category}`;
    }

    await fetchProducts(url);
}

async function nextPage() {

    if (!hasMore) {
        alert("No more products available.");
        return;
    }

    const limit = document.getElementById("limit").value;
    const cursor = document.getElementById("cursor").value;
    const category = document.getElementById("category").value;

    let url = `/products?limit=${limit}`;

    if (cursor !== "") {
        url += `&cursor=${cursor}`;
    }

    if (category !== "") {
        url += `&category=${category}`;
    }

    await fetchProducts(url);
}

async function fetchProducts(url) {

    try {

        const response = await fetch(url);

        if (!response.ok) {
            throw new Error("Failed to fetch products");
        }

        const data = await response.json();

        display(data);

    } catch (err) {

        console.error(err);
        alert("Unable to fetch data from server.");

    }

}

function display(data) {

    const tbody = document.getElementById("tableBody");

    tbody.innerHTML = "";

    if (data.products.length === 0) {

        tbody.innerHTML = `
            <tr>
                <td colspan="5" style="text-align:center">
                    No Products Found
                </td>
            </tr>
        `;

        document.getElementById("nextBtn").disabled = true;
        return;
    }

    data.products.forEach(product => {

        tbody.innerHTML += `
            <tr>
                <td>${product.id}</td>
                <td>${product.name}</td>
                <td>${product.category}</td>
                <td>&#8377; ${product.price}</td>
                <td>${product.quantity}</td>
            </tr>
        `;

    });

    hasMore = data.hasMore;

    document.getElementById("nextBtn").disabled = !hasMore;

    document.getElementById("cursor").value = data.cursor ?? "";

    document.getElementById("cursorDisplay").innerText =
        data.cursor ?? "None";

}

window.onload = function () {
    loadFirstPage();
};