app.cart = {

    songs: [],

    addSongToCart: function(event, beatId, songElement) {
        $.get("http://localhost:60001/addtocart/" + beatId, app.cart.setCart);
        let songElementRight = songElement.children[1];

        let addToCartButton = songElementRight.children[0];
        let removeFromCartButton = songElementRight.children[1];

        addToCartButton.style.display = "none";
        removeFromCartButton.style.display = "inline-block";

        songElement.classList.add("added");
        app.cart.songs.push(beatId);
        event.stopPropagation();
    },

    removeSongFromCart: function(event, beatId, songElement) {
        $.get("http://localhost:60001/removefromcart/" + beatId, app.cart.setCart);

        if (songElement !== null) {
            let songElementRight = songElement.children[1];
            let addToCartButton = songElementRight.children[0];
            let removeFromCartButton = songElementRight.children[1];

            addToCartButton.style.display = "inline-block";
            removeFromCartButton.style.display = "none";

            songElement.classList.remove("added");
        }

        app.cart.songs.splice(app.cart.songs.indexOf(beatId), 1);
        event.stopPropagation();
    },

    getCart: function() {
        $.get("http://localhost:60001/getcart/", app.cart.setCart);
    },

    setCart: function(cart) {
        let $cartTotal = $("#cart-total");
        let price = (cart["totalInCents"] / 100).toFixed(2);
        $cartTotal.html("$" + price);
        $("#cart-modal-total").html($cartTotal.html());
        let checkoutButton = document.getElementById("modal-checkout");
        if (price == 0) {
            checkoutButton.setAttribute("disabled", "true");
        } else {
            checkoutButton.removeAttribute("disabled");
        }

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
    },

    displayModal: function() {
        if (app.cart.songs.length === 0) {
            alert("Your cart is empty!")
        } else {
            app.cart.buildModal();
            document.getElementById("cart-modal").style.display = "block";
        }
    },

    buildModal: function() {
        let modal = document.getElementById("cart-modal-songs");
        modal.innerHTML = "";

        app.cart.songs.forEach(function(song) {
            let modalRow = document.createElement("tr");

            let title = document.createElement("td");
            title.innerHTML = song["title"];
            modalRow.appendChild(title);

            let genre = document.createElement("td");
            genre.innerHTML = song["genre"];
            modalRow.appendChild(genre);

            let artist = document.createElement("td");
            artist.innerHTML = song["owner"]["username"];
            modalRow.appendChild(artist);

            let price = document.createElement("td");
            price.innerHTML = (song["priceInCents"] / 100).toFixed(2);
            modalRow.appendChild(price);

            let remove = document.createElement("td");
            let removeButton = document.createElement("i");
            removeButton.classList.add("glyphicon");
            removeButton.classList.add("glyphicon-remove");
            removeButton.addEventListener("click", function() {
                app.cart.modalRemove(song["id"], modalRow);
            });

            remove.appendChild(removeButton);
            modalRow.appendChild(remove);

            modalRow.classList.add("modal-data-row");
            modal.appendChild(modalRow);

            let total = document.getElementById("cart-total").innerHTML;
            document.getElementById("cart-modal-total").innerHTML = total;
        });
    },

    modalRemove: function(songId, modalRow) {
        let modal = document.getElementById("cart-modal-songs");
        modal.removeChild(modalRow);

        let songElement = app.dom.findSongFromModalRow(modalRow);
        app.cart.removeSongFromCart(songId, songElement);
    }
};