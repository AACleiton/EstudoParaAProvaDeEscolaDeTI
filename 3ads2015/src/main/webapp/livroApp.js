module = angular.module("App",[]);

module.controller("LivroController", ["$scope","$http", function($scope,$http){
        
        $scope.salvar = funcSalvar;
        $scope.atualizar = funcAtualizar;
        $scope.alterar = funcAlterar;
        $scope.excluir = funcDeletar;
        
        $scope.livros = [];
        $scope.livro = {
            id : " ",
            titulo : " ",
            ano : " ",
            peso : " "
            
        };
        $scope.isNovo =  true;
        
        funcAtualizar();
        
        function sucesso(){
            
            alert("sucesso");
            funcAtualizar();
            
        }
        
        function erro(){
            alert("deu ruim");
            funcAtualizar();
        }
        
        function funcAtualizar(){
            $http.get("/livros").success(function(data){
                $scope.livros = data;
            }).error(erro);
        }
        
        function funcAlterar(livro){
            $scope.livro = angular.copy(livro);
            $scope.isNovo = false;
        }
        
        function funcDeletar(livro){
            $http.delete("/livros/" + livro.id).success(sucesso).error(erro);
            
        }
        
        function funcSalvar(){
            if($scope.isNovo){
                $http.post("/livros/salvar",$scope.livro).success(sucesso).error(erro);
                
                $scope.livro = {};
            }
            else{
                $http.put("/livros/" + $scope.livro.id, $scope.livro).success(sucesso).error(erro);
                funcAtualizar();
            }
        }
}]);

