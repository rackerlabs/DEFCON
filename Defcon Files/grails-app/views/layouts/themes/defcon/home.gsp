<!DOCTYPE html>
<html>
    <theme:head/>
    <theme:body>
        <theme:layoutTemplate name="header"/>
        <header style="background-color:black">
            <div class="inner">
                <div class="container">
                    <theme:layoutZone name="banner"/>
                </div>
            </div>  
        </header>
        <div class="container">
            <div class="content">
                <div class="row">
                    <div class="span11">
                        <theme:layoutZone name="body"/>
                    </div>
                </div>
            </div>
            <theme:layoutTemplate name="footer"/>
        </div>
    </theme:body>
</html>