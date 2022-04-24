package com.bestsales.uikit.cards.productCard

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import com.bestsales.uikit.R
import com.skydoves.landscapist.glide.GlideImage
import java.time.format.TextStyle

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProductCard(item: ProductItem, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(0.dp),
        onClick = onClick
    ) {
        ConstraintLayout(
            decoupledConstraints()
        ) {

            val hasSale = item.basePrice.isNotEmpty()
            val style = if (hasSale) {
                androidx.compose.ui.text.TextStyle(
                    color = colorResource(R.color.red),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
            } else {
                androidx.compose.ui.text.TextStyle(
                    color = colorResource(R.color.black),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
            Text(
                text = item.price,
                modifier = Modifier
                    .layoutId("price"),
                style = style
            )

            Text(
                text = stringResource(id = R.string.price_is),
                fontSize = 16.sp,
                modifier = Modifier
                    .layoutId("priceLabel"),
                color = colorResource(R.color.black),
            )

            if (hasSale) {
                Text(
                    text = item.basePrice,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .layoutId("basePrice"),
                    color = colorResource(R.color.black),
                    textDecoration = TextDecoration.LineThrough
                )
            }

            GlideImage(
                modifier = Modifier
                    .defaultMinSize(100.dp, 200.dp)
                    .layoutId("image")
                    .padding(8.dp),
                imageModel = item.imgLink,
                contentScale = ContentScale.FillWidth,
                placeHolder = ColorPainter(Color(R.color.black_20)),
                error = ColorPainter(Color(R.color.black_20))
            )

            Text(
                text = item.title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .layoutId("title")
                    .padding(8.dp),
                color = colorResource(R.color.black),
                maxLines = 2
            )
        }
    }
}

private fun decoupledConstraints(): ConstraintSet {
    return ConstraintSet {
        val image = createRefFor("image")
        val title = createRefFor("title")
        val price = createRefFor("price")
        val priceLabel = createRefFor("priceLabel")
        val basePrice = createRefFor("basePrice")


        constrain(image) {
            top.linkTo(priceLabel.bottom)
            start.linkTo(parent.start)
            bottom.linkTo(parent.bottom)
            width = Dimension.preferredValue(100.dp)
        }

        constrain(title) {
            top.linkTo(priceLabel.bottom)
            linkTo(image.end, parent.end, 0.dp, 0.dp, bias = 0f)
            width = Dimension.fillToConstraints
        }

        constrain(priceLabel) {
            start.linkTo(image.end, 8.dp)
            top.linkTo(parent.top, 8.dp)
        }

        constrain(basePrice) {
            start.linkTo(priceLabel.end, 8.dp)
            top.linkTo(parent.top, 8.dp)
            bottom.linkTo(priceLabel.bottom)
        }

        constrain(price) {
            linkTo(basePrice.end, parent.end, 8.dp, 8.dp, bias = 0f)
            top.linkTo(parent.top, 8.dp)
            bottom.linkTo(priceLabel.bottom)
        }
    }
}