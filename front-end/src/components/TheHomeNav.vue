<template>
    <div>
        <md-toolbar class="md-transparent">
            <div class="md-toolbar-row">
                <div class="md-toolbar-section-start">
                    <h3 class="md-title">你问我答</h3>
                    <md-button to="#">首页</md-button>
                    <md-button to="#">发现</md-button>
                    <md-button to="#">等你来答</md-button>
                </div>
                <vue-instant v-model="searchBoxValue"
                             :suggestions="suggestions"
                             :placeholder="placeholder"
                             :show-autocomplete="true"
                             :suggestion-attribute="suggestion_attribute"
                             type="twitter"
                />
                <div class="md-toolbar-section-end">
                    <md-button>
                        <md-icon>notifications</md-icon>
                    </md-button>
                    <md-button>
                        <md-icon>sms</md-icon>
                    </md-button>

                    <md-menu md-align-trigger>

                        <md-avatar md-menu-trigger>
                            <img src="@/assets/examples/wutengchunzi.png" alt="avatar"/>
                        </md-avatar>
                        <md-menu-content>
                            <md-menu-item>
                                <md-button>
                                    <md-icon>person</md-icon> 我的主页
                                </md-button>
                            </md-menu-item>
                            <md-menu-item>
                                <md-button>
                                    <md-icon>settings</md-icon> 我的设置
                                </md-button>
                            </md-menu-item>
                            <md-menu-item>
                                <md-button>
                                    <md-icon>power_settings_new</md-icon> 退出登录
                                </md-button>
                            </md-menu-item>
                        </md-menu-content>
                    </md-menu>
                </div>
            </div>

        </md-toolbar>

    </div>

</template>

<script>
    export default {
        name: "TheHomeNav",
        data () {
            return {
                searchBoxValue: '',
                suggestions: [
                    {title: "hello"},
                    {title: "world"},
                    {title: 'hi'},
                    {title: 'hii'},
                    {title: 'hiiii'}
                ],
                suggestion_attribute: 'title',
                placeholder: '搜点什么'
            }
        }
    }
</script>

<style scoped>

</style>