
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.echohealth.VirtualEar.MarqueeText
import kotlinx.coroutines.delay

@Composable
fun EarHealthTipsScreen() {
    val Eartips = listOf(
        "Be Cautious with Ear Drops: Use ear drops as directed and consult a doctor if unsure",
        "Keep Ears Dry: Prevent infections by keeping ears dry",
        "Use Noise-Canceling Headphones: They can help reduce the need to turn up the volume",
        "Be Mindful of Age-Related Changes: Hearing can decline with age; regular check-ups are essential",
        "Avoid Loud Sounds: Keep the volume at a safe level",
        "Stay Active: Regular exercise supports overall health, including ear health",
        "Avoid Direct Exposure to Wind: Protect your ears from extreme weather conditions",
        "Use Proper Ear Protection at Work: Especially in noisy workplaces",
        "Avoid Smoking: Smoking can affect hearing",
        "Monitor for Tinnitus: Seek help if you experience ringing in the ears",
        "Consult Audiologists Regularly: Get professional hearing assessments",
        "Avoid Inserting Objects: Don’t insert objects into your ears",
        "Use Soft Earplugs for Sleeping: To protect ears from noise while sleeping",
        "Take Listening Breaks: Give your ears a rest after loud sounds",
        "Use Ear Drops Carefully: Follow instructions for ear drops",
        "Get Regular Check-Ups: Schedule periodic hearing exams",
        "Be Cautious with Medications: Check for hearing side effects",
        "Exercise Your Ears: Regular auditory exercises can help",
        "Healthy Diet: Eat a balanced diet for ear health",
        "Use Earplugs: In loud environments, use earplugs",
        "Keep Headphones Clean: Regularly clean headphones to avoid infections",
        "Monitor Hearing Changes: Report any sudden changes to a professional",
        "Check for Ear Infections: Get prompt treatment for infections",
        "Maintain a Balanced Diet: A balanced diet supports overall ear health",
        "Maintain Good Hygiene: Clean your ears gently and regularly",
        "Avoid Over-the-Counter Ear Cleaners: Consult a professional before using them",
        "Control Blood Pressure: High blood pressure can affect hearing",
        "Protect Ears During Sports: Use appropriate protective gear",
        "Be Cautious with Loud Machinery: Use ear protection around loud machinery",
        "Reduce Stress: Stress can impact ear health and hearing",
        "Get Adequate Sleep: Quality sleep supports overall health, including ear health",
        "Limit Use of Cotton Swabs: They can push earwax deeper into the ear canal",
        "Use Ear Plugs While Swimming: Prevent water from entering the ears",
        "Avoid Excessive Alcohol Consumption: Alcohol can affect ear health",
        "Manage Allergies: Allergies can impact ear health and cause infections",
        "Regularly Check Hearing Aids: Ensure they’re working properly if you use them",
        "Maintain Healthy Blood Sugar Levels: Diabetes can affect hearing",
        "Be Aware of Occupational Hazards: Use appropriate ear protection for job-related noise",
        "Avoid Heavy Lifting: Excessive strain can impact ear health",
        "Be Aware of Family History: Genetic factors can affect hearing",
        "Avoid Using Personal Music Devices at High Volume: Use lower volumes and take breaks",
        "Protect Ears from Cold: Wear hats or ear muffs in cold weather",
        "Stay Hydrated: Proper hydration supports overall ear health",
        "Use Hearing Protection in Concerts: Wear earplugs at live music events",
        "Use Protective Gear in High-Risk Activities: Wear ear protection during activities like shooting",
        "Use Gentle Ear Cleansing Solutions: Opt for solutions recommended by professionals",
        "Be Cautious with Ear Candling: Consult a professional before trying ear candling",
        "Check for Allergic Reactions: Allergies can cause ear problems",
        "Use Hearing Protection While Riding Motorcycles: Wind noise can damage hearing",
        "Avoid Smoking Around Children: Protect children’s ear health from second-hand smoke",
        "Consider Hearing Aids if Necessary: Consult a professional if hearing loss is suspected",
        "Avoid Using Public Pools: They can increase the risk of ear infections",
        "Protect Ears from Dust: Use ear protection in dusty environments",
        "Consult a Professional for Persistent Issues: Seek help for ongoing ear problems",
        "Avoid Excessive Use of Hearing Aids: Use them as directed by a professional",
        "Monitor Hearing in Older Adults: Regular check-ups are crucial as hearing can decline with age",
        "Avoid Listening to Music While Exercising: Use lower volumes during exercise",
        "Keep Ears Clean with Safe Methods: Use gentle cleaning methods for ear health",
        "Check for Ear Injury After Trauma: Get an assessment if you experience ear trauma",
        "Protect Ears from Extreme Heat: Avoid exposing ears to excessive heat",
        "Be Cautious with Home Remedies: Consult professionals before using home remedies",
        "Consider Genetic Factors: Be aware of hereditary ear conditions",
        "Avoid Exposure to Harmful Chemicals: Protect ears from chemicals that could cause harm",
        "Monitor Ear Health After Surgery: Follow post-surgical care instructions",
        "Be Mindful of Environmental Changes: Sudden changes can affect ear health",
        "Avoid Loud Conversations: High-volume conversations can also affect ear health",
        "Use Ear Protection for Long Drives: Protect ears from road noise",
        "Avoid Overuse of Antibiotics: Only use antibiotics as prescribed",
        "Use Safe Listening Habits: Follow safe listening practices for all audio devices",
        "Stay Informed About Ear Health: Keep up-to-date with the latest ear health advice and recommendations",
        "Protect Your Ears: Use ear protection in noisy environments",
        "Educate Yourself on Ear Health: Knowledge about ear care can prevent issues",
        "Avoid Self-Diagnosis: Consult professionals for ear-related issues",
        "Be Cautious with Cleaning Devices: Use safe cleaning methods for ear devices",
        "Educate on Hearing Loss Prevention: Raise awareness about hearing loss prevention",
        "Avoid Putting Ears in Water: Avoid long-term exposure to water without protection",
        "Check Hearing Regularly: Periodic hearing checks are important",
        "Manage Earwax: Consult a professional for excessive earwax",
        "Manage Chronic Conditions: Conditions like diabetes can affect ear health",
        "Maintain a Healthy Weight: Obesity can affect ear health",
        "Protect Ears During Loud Activities: Use ear protection during activities like fireworks",
        "Avoid Long-Term Use of Certain Medications: Some medications can impact hearing",
        "Be Mindful of Ear Health in Children: Ensure children’s ear health is monitored",
        "Consult an ENT Specialist: For persistent or severe ear issues",
        "Limit Exposure to Noise: Avoid prolonged exposure to loud noises",
        "Protect Ears from Loud Music: Use ear protection at concerts or loud events",
        "Get Flu Vaccinations: Prevent flu-related ear infections",
        "Monitor Noise Levels: Use noise level monitors in loud environments",
        "Avoid High-Pitched Sounds: They can be more damaging to hearing",
        "Regularly Clean Ear Devices: Keep hearing aids and earbuds clean to prevent infections",
        "Use Ear Protection for DIY Projects: Wear earplugs when using power tools",
        "Avoid Sudden Loud Noises: Protect ears from abrupt loud sounds",
        "Keep Ears Clean and Dry: Avoid moisture buildup"
    )


    val shuffledTips by remember { mutableStateOf(Eartips.shuffled()) }
    var isUserInteracting by remember { mutableStateOf(false) }
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { shuffledTips.size }
    )
    LaunchedEffect(isUserInteracting) {
        while (true) {
            delay( 10000)
            if (!isUserInteracting) {
                pagerState.animateScrollToPage((pagerState.currentPage + 1) % shuffledTips.size)
            }
        }
    }

    HorizontalPager(
        state = pagerState,
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
    ) { pagevalue ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.DarkGray, shape = RoundedCornerShape(8.dp))
        ) {
            MarqueeText(text = shuffledTips[pagevalue])
        }
    }
}
