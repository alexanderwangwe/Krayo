import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.krayo.art.ui.screens.content_search.components.SearchTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchResultsScreen(
    navController: NavController,
    paddingValues: PaddingValues,
    updateNavState: (Boolean) -> Unit,
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
        modifier = Modifier.fillMaxSize(),
        topBar = {
            SearchTopBar(
                paddingValues = WindowInsets.statusBars.asPaddingValues(),
                navController = navController,
                defaultValue = "Contemporary Art"
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(top = 15.dp)
        ) {

            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues = paddingValues).padding(horizontal = 15.dp),
                columns = GridCells.Fixed(2),
            ) {
                item {
                    Column {
                        Text(
                            "Results for",
                            style = MaterialTheme.typography.bodyMedium,
                        )
                        Text(
                            "“Contemporary Art”",
                            style = MaterialTheme.typography.bodyLarge,
                        )
                    }
                }
                item{
                    Text("")
                }
                items(510) {
                    SearchResultsItem()
                }
            }
        }
    }
}

@Composable
private fun SearchResultsItem() {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .aspectRatio(0.6f).clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.onBackground)
    )
}