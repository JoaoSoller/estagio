var btnMenu = document.querySelector('.li-menu');

btnMenu.addEventListener('click', function(){
    var boxMenu = document.querySelector('.li-opcao-menu');

    boxMenu.classList.toggle('li-visibilidade');
});

function pesquisa(){ 

	 var id = $('#idletra').val();
	 $.ajax({
			method : "GET",
			url : "BUSCAPORLETRA",
			data : "id=" + id ,
			success : function(response) {
		}
		}).fail(function(xhr, status, errorThrown) {
			alert("Erro ao deletar marca por id: " + xhr.responseText);
		});
	
}