// ==version1==
```
class Company {
    - warehouses : List<Warehouse>

    + getWarehouses() : List<Warehouse>
    + addWarehouses(warehouses : Warehouse) : void
    + countTotalVolumeBySupplier(supplierName : String) : Double
    + findProductLocations(productName : String) : List<Map.Entry<String, String>>
}

class Warehouse {
    - city : String
    - address : String
    - surface : Double
    - occupations : List<ProductOccupation>

    + getCity() : String
    + setCity(city : String) : void
    + getAddress() : String
    + setAddress(address : String) : void
    + getSurface() : String
    + setSurface(surface : Double) : void
    + getOccupations() : List<ProductOccupation>
    + addOccupations(occupations : ProductOccupation) : void
    
    + retrieveToxicProductNames() : List<String>
    + retrieveUniqueSuppliers() : List<Supplier>
    + containsProduct(productName : String) : boolean
    
}

class ProductOccupation {
    - volume : Double
    - product : Product

    + getVolume() : String
    + setVolume(volume : Double) : void
    + getProduct() : Product
    + setProduct(product : Product) : void
}

class Product {
    - name : String
    - isToxic : boolean
    - supplier : Supplier

    + getName() : String
    + setName(name : String) : void
    + isToxic() : boolean
    + setToxic(isToxic : boolean) : void
    + getSupplier() : Supplier
    + setSupplier(supplier : Supplier) : void
}

class Supplier {
    - name : String
    - address : String

    + getName() : String
    + setName(name : String) : void
    + getAddress() : String
    + setAddress(address : String) : void
}

Company *-- "*" Warehouse : warehouses
Warehouse *-- "*" ProductOccupation : occupations
ProductOccupation --> "1" Product : product
Product --> "1" Supplier : supplier
```
// ==end==
