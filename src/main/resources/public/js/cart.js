app.cart = {

    songs: [],

    addSongToCart: function(beatId, songElement) {
        $.get("http://localhost:60001/addtocart/" + beatId, app.cart.setCart);
        let songElementRight = songElement.children[1];

        let addToCartButton = songElementRight.children[0];
        let removeFromCartButton = songElementRight.children[1];

        addToCartButton.style.display = "none";
        removeFromCartButton.style.display = "inline-block";

        songElement.classList.add("added");
        app.cart.songs.push(beatId);
    },

    removeSongFromCart: function(beatId, songElement) {
        $.get("http://localhost:60001/removefromcart/" + beatId, app.cart.setCart);
        let songElementRight = songElement.children[1];
        let addToCartButton = songElementRight.children[0];
        let removeFromCartButton = songElementRight.children[1];

        addToCartButton.style.display = "inline-block";
        removeFromCartButton.style.display = "none";

        songElement.classList.remove("added");
        app.cart.songs.splice(app.cart.songs.indexOf(beatId), 1);
    },

    getCart: function() {
        $.get("http://localhost:60001/getcart/", app.cart.setCart);
    },

    setCart: function(cart) {
        let $cartTotal = $("#cart-total");
        let price = (cart["totalInCents"] / 100).toFixed(2);
        $cartTotal.html("$" + price);

        let $cartItems = $("#cartitems");
        $cartItems.html(cart["numberOfItems"]);
        app.cart.songs = cart["beats"];
    },

    contains: function(beatId) {
        for (let i = 0; i < app.cart.songs.length; i++) {
            if (app.cart.songs[i]["id"] === beatId) {
                return true;
            }
        }

        return false;
    }
};