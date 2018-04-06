import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { StockListComponent } from './stock-list.component';
import { StockListDetailComponent } from './stock-list-detail.component';
import { StockListPopupComponent } from './stock-list-dialog.component';
import { StockListDeletePopupComponent } from './stock-list-delete-dialog.component';

export const stockListRoute: Routes = [
    {
        path: 'stock-list',
        component: StockListComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StockLists'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'stock-list/:id',
        component: StockListDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StockLists'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const stockListPopupRoute: Routes = [
    {
        path: 'stock-list-new',
        component: StockListPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StockLists'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'stock-list/:id/edit',
        component: StockListPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StockLists'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'stock-list/:id/delete',
        component: StockListDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StockLists'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
