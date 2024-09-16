package common.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
 fun SimpleTopBar(
    modifier: Modifier = Modifier,
    title: String
) {
     //Jetpack compose topBar currently have the height issue that is why making a custom
     Surface(
         modifier=modifier  .clip(RoundedCornerShape(16.dp)),
         tonalElevation = 8.dp
     ) {
         Row (
             modifier = Modifier
                 .padding(horizontal = 16.dp, vertical = 12.dp)
                 .fillMaxWidth()

         ){
             Text(
                 text = title,
                 style = MaterialTheme.typography.titleLarge
             )
         }
     }

    
}
@Composable
fun SimpleTopBar(
    modifier: Modifier = Modifier,
    title: @Composable ()->Unit,
) {
    //Jetpack compose topBar currently have the height issue that is why making a custom
    Surface(
        modifier=modifier
            .clip(RoundedCornerShape(16.dp))
        ,
        tonalElevation = 8.dp
    ) {
        Row (
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .fillMaxWidth()
        ){
            title()
        }
    }


}