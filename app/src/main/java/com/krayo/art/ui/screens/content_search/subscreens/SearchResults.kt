import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
                .padding(paddingValues)
                .padding(15.dp)
        ) {

            LazyVerticalGrid(
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
fun SearchResultsItem() {
    Surface(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
            .height(300.dp),
        color = MaterialTheme.colorScheme.onBackground,
    ) {
    }
}