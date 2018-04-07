app.cart = {

    addSongToCart: function(beatId) {
        $.get("http://localhost:60001/addtocart/" + beatId, setCart);
    },

    getCart: function() {
        $.get("http://localhost:60001/getcart/", setCart);
    },
};

function setCart(cart) {
    let $cartTotal = $("#cart-total");
    let price = (cart["totalInCents"] / 100).toFixed(2);
    $cartTotal.html("$" + price);

    let $cartItems = $("#cartitems");
    $cartItems.html(cart["numberOfItems"]);
}