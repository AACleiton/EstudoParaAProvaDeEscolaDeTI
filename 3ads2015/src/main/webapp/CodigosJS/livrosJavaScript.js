    module = angular.module("App", []);
    
    module.controller("LivroController", ["$scope","$http", function($scope,$http){
            
            $scope.salvar = salvar;
            $scope.alterar = alterar;
            $scope.excluir = excluir;
            $scope.atualizar = atualizar;
            
            $scope.isNovo = true;
            $scope.livros = [];
            $scope.livro = {};
            
            
            $scope.salvar = function() {
            if ($scope.isNovo) {
                $http.post("/livros", $scope.livro).sucess(sucessoAoSalvar).erro(deuErro);
            } else {
                $http.put("/livros/" + $scope.livro.id, $scope.livro).sucess(sucessoAoAlterar).erro(deuErro());
            }
            
            function sucessoAoSalvar() {
                    alert("Livro cadastrado!");
                    $scope.atualizar();
                    novo();
            }
            function sucessoAoAlterar() {
                    alert("Dados deste livro alterados!");
                    $scope.atualizar();
                    novo();
                }
        };
            
            $scope.atualizar = function(){
                $http.get("/livros")
                        .success(function (data){
                            $scope.livros = data;
                        }).error(deuErro);
            };
            
            $scope.excluir = function(livro) {
                $http.delete("/livros/" + livro.id)
                        .success(function(){
                            alert("Excluido!");
                            $scope.atualizar();
                        }).error(deuErro);
            };
            
            $scope.alterar = function(livro) {
                $scope.livro = angular.copy(livro);
                $scope.isNovo = false;
            };
            
            function deuErro() {
                alert("Erro!!!");
            }
    }])

