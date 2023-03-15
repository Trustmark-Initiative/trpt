Notes on domain objects:

* The persistence layer does not enforce not-null constraints; the service does this.

* The persistence layer does not enforce unqiue constraints; the service does this.

* In order to access dynamic properties of domain classes (id, references to other domain classes) from Java classes, the domain class includes methods such as:

    void propertyHelper(final PropertyType property)
    PropertyType propertyHelper()

  Note that these must contain setProperty and getProperty methods, as setting the property directly does not propagate to the database.

* In order to access dynamic finders of domain classes, the domain class includes methods such as:

    List<DomainType> findAllHelper()


