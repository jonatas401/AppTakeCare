export default function viaCep(cep){
	
	let cepValue = document.getElementById("cep")
	
     function pesquisaCep(){
	
                console.log(cep)
                let ajax = new XMLHttpRequest();
                ajax.open('GET', 'https://viacep.com.br/ws/'+cep+'/json/')
                //console.log(ajax)
                ajax.onreadystatechange = ()=>{
                    //console.log(ajax.readyState)
                    
                    if(ajax.readyState == 4 && ajax.status == 200){
                    let res = ajax.responseText
                    let ajaxOBJ = JSON.parse(res);
                    
                    console.log(ajaxOBJ)
                    document.getElementById('rua').value = ajaxOBJ.logradouro;
                    document.getElementById('bairro').value = ajaxOBJ.bairro;
                    document.getElementById('cidade').value = ajaxOBJ.localidade;
                    document.getElementById('uf').value = ajaxOBJ.uf;
                    }
                }
                ajax.send()
                
			}
              
      cepValue.addEventListener("change", pesquisaCep) 
	
			         
			 
	 }