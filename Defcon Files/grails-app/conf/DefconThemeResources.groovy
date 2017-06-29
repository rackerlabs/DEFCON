
// Put your resources in here
modules = {
    'theme.defcon' {
        dependsOn 'theme.Bootstrap'
        
        // Add your global CSS/JS files here
    }

    'theme.defcon.home' {
        dependsOn 'theme.Bootstrap.home'
        // Add your 'home' specific CSS/JS files here
    }

    'theme.defcon.sidebar' {
        dependsOn 'theme.Bootstrap.sidebar'
        // Add your 'sidebar' specific CSS/JS files here
    }

    'theme.defcon.report' {
        dependsOn 'theme.Bootstrap.report'
        // Add your 'report' specific CSS/JS files here
    }

    'theme.defcon.dataentry' {
        dependsOn 'theme.Bootstrap.dataentry'
        // Add your 'dataentry' specific CSS/JS files here
    }

    'theme.defcon.dialog' {
        dependsOn 'theme.Bootstrap.dialog'
        // Add your 'dialog' specific CSS/JS files here
    }

    'theme.defcon.main' {
        dependsOn 'theme.Bootstrap.main'
        // Add your 'main' specific CSS/JS files here
    }
}