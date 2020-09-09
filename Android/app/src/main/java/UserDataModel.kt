import android.graphics.Picture

data class UserDataModel(
    var firstName: String,
    var lastName: String,
    var age: Int? = null,
    var city: String? = null,
    var country: String? = null,
    var heightInches: Int? = null,
    var weightLbs: Int? = null,
    var male: Boolean? = null,
    var profilePicture: Picture? = null,
    var activityLevel: String? = null,
    var weightChangeGoalPerWeek: Float? = null
)