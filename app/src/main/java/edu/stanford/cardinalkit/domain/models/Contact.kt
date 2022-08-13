package edu.stanford.cardinalkit.domain.models

data class Contact(
    var id: String,
    var title: String,
    var firstName: String,
    var lastName: String,
    var role: String,
    var phoneNumber: String,
    var smsNumber: String,
    var description: String,
    var email: String,
    var streetAddress: String,
    var city: String,
    var state: String,
    var country: String,
    var postalCode: String
)