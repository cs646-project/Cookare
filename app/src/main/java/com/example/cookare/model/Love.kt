package com.example.cookare.model

import androidx.annotation.DrawableRes
import androidx.compose.runtime.mutableStateListOf
import com.example.cookare.R

data class Love(
    val title: String,
    val tags: String,
    val ingredients: String,
    val cover_url: String,
    val description: String
)
val loves = mutableStateListOf(
    Love("Roasted Chicken", "veggie", "chicken,butter,honey,garlic", "https://imagesvc.meredithcorp.io/v3/mm/image?url=https%3A%2F%2Fstatic.onecms.io%2Fwp-content%2Fuploads%2Fsites%2F9%2F2021%2F08%2F12%2Fjulia-child-favorite-roast-chicken-XL-RECIPE0313.jpg", "One Pan Garlic Roasted Chicken and Baby Potatoes. One pan garlic roasted chicken and baby potatoes is an easy to make, delicious, and wholesome meal for the entire family. Prep this sheet pan in 10 minutes. and a bright kick of white wine. It’s the kind of meal you can look forward to making and eating!"),
    Love("Fry egg", "meat", "egg,cream,lettuce", "https://www.jessicagavin.com/wp-content/uploads/2020/09/how-to-fry-an-egg-3.jpg", "This White Bean Chicken Soup is a slurpable, soothing, chicken broth-based soup, that's filled with white beans, roasted chicken, savory herbs, and hearty root vegetables. It's craveable on cold and rainy days."),
    Love("Tomato fish", "meat", "tomato,fish,butter,ketchup", "https://cafedelites.com/wp-content/uploads/2017/07/Pan-Seared-Fish-In-Tomato-Olive-Sauce-IMAGE-1.jpg", "This White Bean Chicken Soup is a slurpable, soothing, chicken broth-based soup, that's filled with white beans, roasted chicken, savory herbs, and hearty root vegetables. It's craveable on cold and rainy days."),
    Love("Butter Cookie", "sweet", "chocolate, flour, butter, egg", "https://images.aws.nestle.recipes/resized/5b069c3ed2feea79377014f6766fcd49_Original_NTH_Chocolate_Chip_Cookie_448_448.jpg", "Prepare dough as above. Divide in half; wrap in waxed paper. Refrigerate for 1 hour or until firm. Shape each half into 15-inch log; wrap in wax paper. Refrigerate for 30 minutes.* Preheat oven to 375° F. Cut into 1/2-inch-thick slices; place on ungreased baking sheets. Bake for 8 to 10 minutes or until golden brown. Cool on baking sheets for 2 minutes; remove to wire racks to cool completely. Makes about 5 dozen cookies. * May be stored in refrigerator for up to 1 week or in freezer for up to 8 weeks."),

    Love(
        "Tomato pasta", "veggie", "pasta,pepper,pasta", "https://www.lastingredient.com/wp-content/uploads/2016/08/burst-tomato-pasta15-819x1024.jpg",
        """This White Bean Chicken Soup is a slurpable, soothing, chicken broth-based soup, that's filled with white beans, roasted chicken, savory herbs, and hearty root vegetables. It's craveable on cold and rainy days.""".trimIndent()
    )
)
val loves1 = mutableStateListOf(
    Love("Roasted Chicken", "veggie", "chicken,butter,honey,garlic", "https://imagesvc.meredithcorp.io/v3/mm/image?url=https%3A%2F%2Fstatic.onecms.io%2Fwp-content%2Fuploads%2Fsites%2F9%2F2021%2F08%2F12%2Fjulia-child-favorite-roast-chicken-XL-RECIPE0313.jpg", "One Pan Garlic Roasted Chicken and Baby Potatoes. One pan garlic roasted chicken and baby potatoes is an easy to make, delicious, and wholesome meal for the entire family. Prep this sheet pan in 10 minutes. and a bright kick of white wine. It’s the kind of meal you can look forward to making and eating!"),

    Love(
        "Tomato pasta", "veggie", "pasta,pepper,pasta", "https://www.lastingredient.com/wp-content/uploads/2016/08/burst-tomato-pasta15-819x1024.jpg",
        """This White Bean Chicken Soup is a slurpable, soothing, chicken broth-based soup, that's filled with white beans, roasted chicken, savory herbs, and hearty root vegetables. It's craveable on cold and rainy days.""".trimIndent()
    )
)
val loves2 = mutableStateListOf(

    Love("Fry egg", "meat", "egg,cream,lettuce", "https://www.jessicagavin.com/wp-content/uploads/2020/09/how-to-fry-an-egg-3.jpg", "This White Bean Chicken Soup is a slurpable, soothing, chicken broth-based soup, that's filled with white beans, roasted chicken, savory herbs, and hearty root vegetables. It's craveable on cold and rainy days."),
    Love("Tomato fish", "meat", "tomato,fish,butter,ketchup", "https://cafedelites.com/wp-content/uploads/2017/07/Pan-Seared-Fish-In-Tomato-Olive-Sauce-IMAGE-1.jpg", "This White Bean Chicken Soup is a slurpable, soothing, chicken broth-based soup, that's filled with white beans, roasted chicken, savory herbs, and hearty root vegetables. It's craveable on cold and rainy days.")

)
val loves3 = mutableStateListOf(

    Love("Butter Cookie", "sweet", "chocolate, flour, butter, egg", "https://images.aws.nestle.recipes/resized/5b069c3ed2feea79377014f6766fcd49_Original_NTH_Chocolate_Chip_Cookie_448_448.jpg", "Prepare dough as above. Divide in half; wrap in waxed paper. Refrigerate for 1 hour or until firm. Shape each half into 15-inch log; wrap in wax paper. Refrigerate for 30 minutes.* Preheat oven to 375° F. Cut into 1/2-inch-thick slices; place on ungreased baking sheets. Bake for 8 to 10 minutes or until golden brown. Cool on baking sheets for 2 minutes; remove to wire racks to cool completely. Makes about 5 dozen cookies. * May be stored in refrigerator for up to 1 week or in freezer for up to 8 weeks.")


)