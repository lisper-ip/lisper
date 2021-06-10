package app.lonzh.lisper.ext

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

/**
 *
 * @ProjectName:    lisper
 * @Description:    描述
 * @Author:         Lisper
 * @CreateDate:     5/28/21 11:50 AM
 * @UpdateUser:     Lisper：
 * @UpdateDate:     5/28/21 11:50 AM
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
fun Fragment.nav(id: Int, isLogin: Boolean = false){
    findNavController().navigate(id)
}

fun Fragment.back(){
    findNavController().popBackStack()
}

fun Fragment.back(destinationId: Int, inclusive: Boolean){
    findNavController().popBackStack(destinationId, inclusive)
}