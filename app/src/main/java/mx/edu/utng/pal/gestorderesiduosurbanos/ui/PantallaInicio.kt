package mx.edu.utng.pal.gestorderesiduosurbanos.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun PantallaInicio(navController: NavHostController) {
    Scaffold(
        containerColor = Color(0xFF4CAF50)
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Text(
                    text = "Gestor de Residuos Urbanos",
                    fontSize = 29.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(32.dp))

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    FuncionesItem("Consultar ubicación de botes")
                    FuncionesItem("Ver horarios de recolección")
                    FuncionesItem("Reportar incidencias")
                }
            }

            Button(
                onClick = { navController.navigate("mapa") },
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(24.dp)
            ) {
                Text("Comenzar", color = Color(0xFF4CAF50), fontSize = 18.sp)
            }
        }
    }
}

@Composable
fun FuncionesItem(texto: String) {
    Text(
        text = texto,
        fontSize = 18.sp,
        color = Color.White,
        modifier = Modifier
            .padding(vertical = 4.dp)
            .fillMaxWidth(),
        textAlign = TextAlign.Center
    )
}
