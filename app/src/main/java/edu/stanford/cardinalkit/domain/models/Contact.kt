package edu.stanford.cardinalkit.domain.models

data class Contact(
    var id: String,
    var name: String,
    var title: String,
    var phoneNumber: String,
    var textNumber: String,
    var description: String,
    var email: String,
    var addressLineOne: String,
    var addressLineTwo: String
)