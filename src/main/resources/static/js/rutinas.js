window.addCard = function(formulario) { 
    const id = formulario.querySelector('input[name="idProducto"]').value; 
    const url = '/carrito/agregar/' + encodeURIComponent(id); 
    $("#resultsBlock").load(url); 
}; 