# Avaliação Beca Mobile Android

Para a implementação do aplicativo foi utilizado o MVVM como padrão, para as requisições http utilizei o retrofi por ter afinidade com a ferramenta devido os usos nos cursos ofertados no bootcamp e para o carregamento de imagens escolhi o coil que foi uma ferramenta vista tabém no bootcamp. 

Encontrei alguns desafios no decorrer da implementalção. Na utilização do retrofit, para fazer a requisição na api que detalhava cada filme era necessário a utilização de um id para a identificação e como não tenho conhecimento amplo na ferramenta foi necessário a leitura da documentação da ferramenta para conseguir realizar a request. Na "DetailViewModel" precisei utilizar o sharedpreferences para armezenar os dados da request e posteriormente utiliza-los na "MovieDetailActivity" pois não estava conseguindo trafegar os dados da "DetailViewModel" para a "MovieDetailActivity".


