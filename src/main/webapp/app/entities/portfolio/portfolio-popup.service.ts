import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { Portfolio } from './portfolio.model';
import { PortfolioService } from './portfolio.service';

@Injectable()
export class PortfolioPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private portfolioService: PortfolioService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.portfolioService.find(id)
                    .subscribe((portfolioResponse: HttpResponse<Portfolio>) => {
                        const portfolio: Portfolio = portfolioResponse.body;
                        portfolio.purchaseDate = this.datePipe
                            .transform(portfolio.purchaseDate, 'yyyy-MM-ddTHH:mm:ss');
                        portfolio.lastUpdated = this.datePipe
                            .transform(portfolio.lastUpdated, 'yyyy-MM-ddTHH:mm:ss');
                        this.ngbModalRef = this.portfolioModalRef(component, portfolio);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.portfolioModalRef(component, new Portfolio());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    portfolioModalRef(component: Component, portfolio: Portfolio): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.portfolio = portfolio;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
