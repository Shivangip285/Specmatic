import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.concurrent.atomic.AtomicInteger
import javax.validation.Valid

@RestController
@RequestMapping("/products")
class ProductController {

    private val productStore = mutableMapOf<Int, Product>()
    private val idCounter = AtomicInteger(1)

    @GetMapping("/products")
    fun getProductsByType(@PathVariable type: String?): ResponseEntity<Any> {
        var type="gadget"
        println("abc")
        return if (type == null || type !in listOf("gadget", "book", "food", "other")) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponseBody("Invalid product type", 400, "Bad Request", "/products"))
        } else {
            val filteredProducts = productStore.values.filter { it.type == type }
            ResponseEntity.ok(filteredProducts)
        }
    }

    @PostMapping("/counter")
    fun createCount(@RequestBody productDetails: String): ResponseEntity<String> {
        return ResponseEntity.ok().body(productDetails)
    }
    @PostMapping("")
    fun createProduct(@RequestBody productDetails: ProductDetails): ResponseEntity<Any> {
        println(productDetails);
        if (productDetails.name.isBlank() || productDetails.type !in listOf("gadget", "book", "food", "other")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponseBody("Invalid product details", 400, "Bad Request", "/products"))
        }

        val productId = idCounter.getAndIncrement()
        val product = Product(productId, productDetails.name, productDetails.type, productDetails.inventory)
        productStore[productId] = product

        return ResponseEntity.status(HttpStatus.CREATED).body(ProductId(productId))
    }
    data class ProductDetails(
            val name: String,
            val type: String,
            val inventory: Int
    )

    data class Product(
            val id: Int,
            val name: String,
            val type: String,
            val inventory: Int
    )

    data class ProductId(
            val id: Int
    )

    data class ErrorResponseBody(
            val error: String,
            val status: Int,
            val path: String,
            val timestamp: String = java.time.LocalDateTime.now().toString()
    )

}
