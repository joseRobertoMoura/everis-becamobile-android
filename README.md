# Avalia��o Beca Mobile Android

Para a implementa��o do aplicativo foi utilizado o MVVM como padr�o, para as requisi��es http utilizei o retrofi por ter afinidade com a ferramenta devido os usos nos cursos ofertados no bootcamp e para o carregamento de imagens escolhi o coil que foi uma ferramenta vista tab�m no bootcamp. 

Encontrei alguns desafios no decorrer da implemental��o. Na utiliza��o do retrofit, para fazer a requisi��o na api que detalhava cada filme era necess�rio a utiliza��o de um id para a identifica��o e como n�o tenho conhecimento amplo na ferramenta foi necess�rio a leitura da documenta��o da ferramenta para conseguir realizar a request. Na "DetailViewModel" precisei utilizar o sharedpreferences para armezenar os dados da request e posteriormente utiliza-los na "MovieDetailActivity" pois n�o estava conseguindo trafegar os dados da "DetailViewModel" para a "MovieDetailActivity".


