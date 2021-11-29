sealed abstract class SecretSantaHeaders(val header: String)

case object Name extends SecretSantaHeaders("NAME")

case object Exclude extends SecretSantaHeaders("EXCLUDE")
