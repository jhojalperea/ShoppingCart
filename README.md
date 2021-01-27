SHOPPINGCART

El proyecto esta desarrollado en Spring Boot de Java y usa Spring Batch, Cache, JPA, la base de datos que usa es un H2.
Los datos a la base de datos son los siguientes:

Cadena de conexion: 
url: jdbc:h2:mem:ShoppinCart
username: sa
password: password

El arrancar la aplicación inserta la información del csv a la base de datos.


Peticiones servicios REST:

Estas 3 apis estan paginadas y los 2 ultimos parametros de cada una son opcionales y reperesentan en su orden (la pagina y el tamaño de la pagina) ,la primera pagina es la 0

Consultar por coincidencia de producto
GET: http://localhost:8080/product/filterByName/wei/0/4

Consultar por marca
GET: http://localhost:8080/product/filterByBrand/lg/0/2

Consultar por preciodesde a preciohasta
GET: http://localhost:8080/product/filterByPrice/0/900000/2/4



Para las siguientes 4 apis el userId que es un UUID representa al id del usuario logeado, las apis soportan multiples usuarios distintos realizando compras

Agregar producto al carrito de compras
POST: http://localhost:8080/cart/addProduct
{
	"userId": "6959a38d-9317-49a6-9ae4-5d60c358fd1d",
	"cartProducts": [{
		"idProduct": 1,
		"quantity": 2
	}]
}

Listar productos del carro de compras
GET: http://localhost:8080/cart/listProducts/6959a38d-9317-49a6-9ae4-5d60c358fd1d

Limpiar carro de compras
GET: http://localhost:8080/cart/clear/6959a38d-9317-49a6-9ae4-5d60c358fd1d

Realizar compra del carro de compras
GET: http://localhost:8080/cart/buy/6959a38d-9317-49a6-9ae4-5d60c358fd1d
